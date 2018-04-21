const models = require('../models')

module.exports = async (req, res, next) => {
  req.checkBody('token', 'token is reqired')
  const userInfo = await models.User.findOne({
    where: { token: req.body.token },
    attributes: ['id']
  })
  if (userInfo) {
    next()
  } else {
    next({error: 'auth error!'})
  }
}
