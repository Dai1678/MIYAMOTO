const express = require('express')
const router = express.Router()
const models = require('../models')
const passwordHash = require('password-hash')
const crypto = require('crypto')

router.post('/login', async (req, res, next) => {
  req.checkBody('email', 'email is required')
  req.checkBody('password', 'password is required')

  const errors = req.validationErrors()
  if (errors) return next(errors)

  const userInfo = await models.User.findOne({
    where: { email: req.body.email }
  })

  if (!userInfo || !passwordHash.verify(req.body.password, userInfo.passHash)) return next({error: 'auth error'})
  res.json({ ok: 1, token: userInfo.accessToken })
})

router.post('/register', async (req, res, next) => {
  req.checkBody('email', 'email is required')
  req.checkBody('password', 'password is required')
  req.checkBody('name', 'name is required')

  const errors = req.validationErrors()
  if (errors) return next(errors)

  const userInfo = await models.User.findOne({
    where: { email: req.body.email }
  })
  if (userInfo) return next({ error: 'already registered' })
  const passHash = passwordHash.generate(req.body.password)
  const accessToken = crypto.randomBytes(16).toString('hex')

  await models.User.create({
    name: req.body.name,
    email: req.body.email,
    passHash,
    accessToken
  })

  res.json({ ok: 1, token: accessToken })
})

module.exports = router
