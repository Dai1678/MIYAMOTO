'use strict'
module.exports = (sequelize, DataTypes) => {
  var User = sequelize.define('User', {
    name: {
      type: DataTypes.STRING,
      allowNull: false
    },
    email: {
      type: DataTypes.STRING,
      allowNull: false
    },
    passHash: {
      type: DataTypes.STRING,
      allowNull: false
    },
    iconUrl: {
      type: DataTypes.STRING
    },
    accessToken: {
      type: DataTypes.STRING,
      allowNull: false
    }
  })
  User.associate = (models) => {
    // associations can be defined here
  }
  return User
}
