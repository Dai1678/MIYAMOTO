const express = require('express')
const router = express.Router()
const models = require('../models')
const authFilter = require('../filters/auth_filter')

router.get('/:userId', authFilter, async (req, res, next) => {
  req.checkParams('userId', 'userId is reqired')

  const errors = req.validationErrors()
  if (errors) return next(errors)

  const user = models.User.findById(req.param.userId, { attributes: ['name'] })

  if (!user) return next({error: 'not found'})
  res.json({ ok: 1, user })
})

router.post('/:userId/pasilistReview/create', authFilter, async (req, res, next) => {
  req.checkParams('userId', 'userId is reqired')
  const errors = req.validationErrors()
  if (errors) return next(errors)

  await models.PasilistReview.create({
    userId: req.params.userId,
    speed: ~~req.body.speed,
    quality: ~~req.body.quality,
    humanNature: ~~req.body.humanNature
  })

  res.json({ ok: 1 })
})

router.get('/:userId/pasilistReview/avarage', authFilter, async (req, res, next) => {
  req.checkParams('userId', 'userId is reqired')
  const errors = req.validationErrors()
  if (errors) return next(errors)

  const reviewAvg = await models.PasilistReview.findOne({
    where: { userId: req.params.userId },
    attributes: [
      'name',
      [models.sequelize.fn('avg', models.sequelize.col('speed')), 'avgSpeed'],
      [models.sequelize.fn('avg', models.sequelize.col('quality')), 'avgQuality'],
      [models.sequelize.fn('avg', models.sequelize.col('humanNature')), 'avgHumanNature']
    ]
  })

  res.json({
    ok: 1,
    speed: reviewAvg.get('avgSpeed') || 0,
    quality: reviewAvg.get('avgQuality') || 0,
    humanNature: reviewAvg.get('avgHumanNature') || 0
  })
})

module.exports = router
