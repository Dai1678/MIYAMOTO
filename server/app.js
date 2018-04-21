const express = require('express')
const path = require('path')
const cookieParser = require('cookie-parser')
const logger = require('morgan')
const expressValidator = require('express-validator')
require('express-async-errors')

var app = express()

// view engine setup
app.set('views', path.join(__dirname, 'views'))
app.set('view engine', 'ejs')

app.use(logger('dev'))
app.use(express.json())
app.use(express.urlencoded({ extended: false }))
app.use(cookieParser())
app.use(express.static(path.join(__dirname, 'public')))
app.use(expressValidator())

app.use('/', require('./routes/index'))
app.use('/auth', require('./routes/auth'))

if (process.env.NODE_ENV !== 'production') {
  app.listen(3000)
  console.log('MIYAMOTO Server is started!! Port:3000')
} else {
  app.listen(80)
  console.log('MIYAMOTO Server is started!! Port:80')
}

// catch 404 and forward to error handler
app.use((req, res, next) => {
  const err = new Error('Not Found')
  err.status = 404
  next(err)
})

// error handler
app.use((err, req, res, next) => {
  if (err.status !== 404) console.log(err)
  res.locals.message = err.message
  res.status(err.status || 500)
  res.format({
    json: (req, res) => {
      if (err.status === 404) err.error = err.message
      res.json({ error: err.error || 'Internal Server Error! Please check server log!' })
    },
    html: (req, res) => { err.status === 404 ? res.render('not_found') : res.render('error') }
  })
})

module.exports = app
