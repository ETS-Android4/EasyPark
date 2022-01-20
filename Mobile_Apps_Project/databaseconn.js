const mysql = require('mysql');

connect = ()=>{
const mysqlcon = mysql.createConnection({
    host: "localhost",
    user: "admin",
    password: "password",
    database : "Mobile_App_Project"} );
    return mysqlcon
}
module.exports = connect; 