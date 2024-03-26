
const { Level } = require('level')
export const db = new Level('cires', { valueEncoding: 'json' })

