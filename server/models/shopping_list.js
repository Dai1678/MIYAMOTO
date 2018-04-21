'use strict'
module.exports = (sequelize, DataTypes) => {
  var ShoppingList = sequelize.define('ShoppingList', {
    pasiRequestId: {
      type: DataTypes.INTEGER,
      allowNull: false
    },
    title: {
      type: DataTypes.STRING,
      allowNull: false
    },
    category: {
      type: DataTypes.INTEGER,
      allowNull: false
    },
    count: {
      type: DataTypes.INTEGER,
      defaultValue: 1
    },
    imageUrl: DataTypes.STRING
  }, {})
  ShoppingList.associate = (models) => {
    // associations can be defined here
  }
  return ShoppingList
}
