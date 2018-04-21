const models = require('../models')

module.exports = async (req, res, next) => {
  const userInfo = await models.User.findOne({
    where: { accessToken: req.body.token || req.query.token },
    attributes: ['id']
  })
  if (userInfo) {
    next()
  } else {
    next({error: 'auth error!'})
  }
}
