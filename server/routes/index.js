const express = require('express')
const router = express.Router()

router.get('/', (req, res, next) => {
  res.json({ok: 1, message: 'Miyamoto!'})
})

module.exports = router
