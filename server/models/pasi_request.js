'use strict'
module.exports = (sequelize, DataTypes) => {
  var PasiRequest = sequelize.define('PasiRequest', {
    userId: {
      type: DataTypes.INTEGER,
      allowNull: false
    },
    totalAmount: {
      type: DataTypes.INTEGER,
      allowNull: false
    },
    timeLimit: {
      type: DataTypes.DATE,
      allowNull: false
    },
    address: {
      type: DataTypes.STRING,
      allowNull: false
    },
    status: {
      type: DataTypes.INTEGER,
      allowNull: false,
      defaultValue: 0
    }
  }, {})

  PasiRequest.WAIT_FOR_PASILIST = 0
  PasiRequest.INPROGRESS = 1
  PasiRequest.FINISH = 2
  PasiRequest.DELETED = 3

  PasiRequest.associate = (models) => {
    PasiRequest.belongsTo(models.User, { as: 'user' })
  }
  return PasiRequest
}
