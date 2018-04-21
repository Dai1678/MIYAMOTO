const models = require('../models')

module.exports = async (req, res, next) => {
  const userInfo = await models.User.findOne({
    where: { token: req.body.token || req.query.token },
    attributes: ['id']
  })
  if (userInfo) {
    next()
  } else {
    next({error: 'auth error!'})
  }
}
