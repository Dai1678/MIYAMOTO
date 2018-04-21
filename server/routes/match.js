const express = require('express')
const router = express.Router()
const models = require('../models')
const authFilter = require('../filters/auth_filter')

router.post('/request', authFilter, async (req, res, next) => {
  req.checkBody('timeLimit', 'timeLimit is required')
  req.checkBody('address', 'address is required')
  req.checkBody('totalAmount', 'totalAmount is required')

  const errors = req.validationErrors()
  if (errors) return next(errors)

  const shoppingLists = req.body.shoppingLists
  const user = await models.User.findOne({ where: { accessToken: req.body.token } })
  const pasiRequest = await models.PasiRequest.findOne({ where: { userId: user.id } })
  if (pasiRequest) return next({ error: 'request already exists' })
  
  await models.sequelize.transaction(async t => {
    const request = await models.PasiRequest.create({
      userId: user.id,
      timeLimit: req.body.timeLimit,
      address: req.body.address,
      totalAmount: ~~req.body.totalAmount,
      status: models.PasiRequest.WAIT_FOR_PASILIST
    }, { transaction: t })

    await Promise.all(shoppingLists.map(async data => {
      return models.ShoppingList.create({
        pasiRequestId: request.id,
        title: data.title,
        count: ~~data.count,
        category: 1,
        imageUrl: null
      }, { transaction: t })
    }))
  })

  res.json({ ok: 1 })
})

router.get('/pasiList', async (req, res, next) => {
  const pasiRequests = await models.PasiRequest.findAll({
    include: [
      {
        model: models.User,
        required: true,
        attributes: ['name', 'iconUrl'],
        as: 'user'
      }
    ],
    where: { status: models.PasiRequest.WAIT_FOR_PASILIST }
  })

  const shoppingLists = await models.ShoppingList.findAll({
    where: {
      pasiRequestId: pasiRequests.map(request => request.id)
    },
    attributes: ['id', 'pasiRequestId']
  })

  pasiRequests.forEach(request => {
    request.shoppingListId = shoppingLists.find(list => list.pasiRequestId === request.id)
  })
  res.json(Object.assign({ ok: 1 }, pasiRequests))
})

module.exports = router