const express = require("express")
const Connection = require("./databaseconn")
const BodyParser = require("body-parser")

const app = express();
mysqlcon = Connection();
app.use(BodyParser.json())

let count = 0 
const main = async function () {
    for (let val = 1 ; val <= 5 ; val ++){
        var current_count; 
        var avg ;
        let status; 
        var query = `SELECT poll_total/poll_count as avg , current_count , total_count FROM parking WHERE id = "${val}"`;
        mysqlcon.query(query, async function (err, result) {
            if (err) throw err;
            total_count = result[0]["total_count"]
            current_count = result[0]["current_count"] / (total_count) * 100
            avg = result[0]["avg"]
            if (avg == null || Math.abs(current_count-avg) < 10)
            {
                status =  current_count
            }
            else{
                status =  avg
            }
            var sql = `UPDATE parking SET status = "${ret_status(status)}", poll_count = 0 ,  poll_total = 0 WHERE id = "${val}"`;
            mysqlcon.query(sql, async function (err, result) {
                if (err) throw err;
                });
            });
        }  
    }


setInterval(function() {
    main().then(()=>console.log("Done"));
}, 2000);


const ret_status = (ret_value) =>{
    if (ret_value >= 0 && ret_value <= 10)
        return"Empty"
    else if (ret_value > 10 && ret_value <= 40)
    {
        return "Almost Empty"
    }
    else if (ret_value > 40 && ret_value <= 75)
        return "Halfway Filled"
    
    else if (ret_value > 75 && ret_value < 90)
        return "Almost Filled"

    else if (ret_value >= 90 && ret_value<= 100)
        return "No Parking"
}



app.get("/allParkings", function (req, res) {
    let query = "Select name , status from parking;"
    var status = new Map()
    mysqlcon.query(query, function (err, result) {
        if (err) {
            res.send(err);
        }
        for (var i of result){
            status[i["name"]] = i["status"]
        }
        res.send(JSON.stringify(status))
    });
});

// Get parking id for a specific parking based on name
app.get("/parkingID/:name", function (req, res) {
    let park_name = req.params.name
    let query = `SELECT id FROM parking WHERE name = "${park_name}"; `

    mysqlcon.query(query, function (err, result) {
        if (err) {
            res.send(err);
        }
        res.send(result);
    });
});

app.get("/getUserParking/:user_id" , (req, res)=>{

    let user_id = req.params.user_id
    let query = `SELECT parking_space_id as id from user_info WHERE student_id = "${user_id}"`
    mysqlcon.query(query, function (err, result) {
        if (err) {
            return res.send(err);
        } else {
            if (result[0]["id"] == null)
                {
                    res.status(200)
                    res.send({"message" : "Not Parked"})
                }
            else{
                    res.status(200)
                    res.send({"message" : result[0]["id"]})
                }
            }
        });
    })

//Post parking ID to user (Put parking id into users table)
app.post("/updateUserParking/:user_id/:parking_id", function (req, res) {
    let user_id = req.params.user_id
    let body2 = req.params.parking_id
    let query = `UPDATE user_info SET parking_space_id = "${body2}" WHERE  student_id = "${user_id}"`;
    mysqlcon.query(query, function (err, result) {
        if (err) {
            return res.send(err);
        } else {

            let returnedObject = {};
            // Your code here
            return res.json(returnedObject);
        }
    });
});

app.post("/checkout/:user_id", function (req, res) {
    let user_id = req.params.user_id
    let query = `UPDATE user_info SET parking_space_id = null WHERE  student_id = "${user_id}"`;
    let querycheck = `SELECT parking_space_id as id from user_info WHERE student_id = "${user_id}"`
    mysqlcon.query(querycheck, function (err, result) {
        if (err) {
            return res.send(err);
        } else {
            if (result[0]["id"] == null)
                {res.status(400)
                res.send("Not Parked")}
            else
                mysqlcon.query(query, function (err, result) {
                    if (err) {
                        return res.send(err);
                    } 
                    else{
                        res.status(200);
                        res.send("Parked")
                    }
            });
        }
    });
});



//Update parking current counter
app.post("/updateCurrentCount/:num/:parking_id", function (req, res) {
    let inc_dec = req.params.num
    let park_id = req.params.parking_id
    let query1 = `Select current_count , total_count from parking WHERE id = "${park_id}";`

    mysqlcon.query(query1, function (err, result) {
        if (err) {
            res.send(err);
        }
        else{
            if(result[0]["current_count"] == result[0]["total_count"]) 
                res.send("Full")
            else{
            let query = `UPDATE parking SET current_count = current_count + "${inc_dec}" WHERE  id = "${park_id}"`;
            mysqlcon.query(query, function (err, result) {
                if (err) {
                    res.status(400);
                    return res.send(err);
                } else {
                    res.status(200); 
                    res.send("Done")
                }
            });
            }
        }
    });
});

//Get parking status
app.get("/parkingStatus/:parking_id", function (req, res) {
    let park_id = req.params.parking_id
    let query = `Select status from parking WHERE id = "${park_id}";`

    mysqlcon.query(query, function (err, result) {
        if (err) {
            res.send(err);
        }
        res.send(result);
    });
});


//Get offer_points
app.get("/get_offer_points/:offer_id", function (req, res) {
    let offerID = req.params.offer_id
    let query = `Select points from vendor_offers WHERE offer_id = "${offerID}";`

    mysqlcon.query(query, function (err, result) {
        if (err) {
            res.send(err);
        }
        res.send(result[0]);
    });
});


//Get offer_points
app.get("/user_info/:user_id", function (req, res) {
    let userID = req.params.user_id
    let query = `Select student_id, username, email, phone_num, points FROM user_info WHERE student_id = "${userID}";`

    mysqlcon.query(query, function (err, result) {
        if (err) {
            res.send(err);
        }
        res.send(result);
    });
});

app.post("/change_points/:id/:points" , (req , res)=>{
    const id = req.params.id; 
    const points = req.params.points
    let query = `SELECT points from user_info WHERE student_id = "${id}"`
    mysqlcon.query(query, function (err, result) {
        if (err) throw err
        let user_points = result[0]["points"]
        let final = user_points + parseInt(points)
        if(final < 0){
            res.status(400) 
            res.send("Insufficient Points")
        } 
        else{
            query = `UPDATE user_info SET points = ${final} WHERE student_id = "${id}"`
            mysqlcon.query(query, function (err, result) {
                if (err) throw err
                res.send("Done")
            }); 
        }
        });
});




app.post ("/send_new_report/:parking_id/:percent", function(req,res){
    var parkingID = req.params.parking_id;
    var percentage = req.params.percent;
    var sql = `UPDATE parking SET poll_count = poll_count + 1, poll_total = poll_total + ${percentage} WHERE id = ${parkingID}`;
    mysqlcon.query(sql, function (err, result) {
        if (err) throw err;
        res.end("1");
    });
});

app.get("/signin/:email" , (req, res)=>{
    var email = req.params.email
    var sql = `SELECT email from user_info WHERE email = "${email}"`;
    mysqlcon.query(sql, function (err, result) {
        if (err) throw err;

        if (result[0] === undefined){
            res.status(400)
            res.send("Not Parked");
        }
        else{
            res.status(200)
            res.send();
        }
    });
})

app.post("/store_data/:name/:phone/:email/:id" , (req,res)=>{

    let name = req.params.name.split("+").join(" ");
    let phone = req.params.phone
    let email = req.params.email
    let id = req.params.id

    let query = `INSERT INTO user_info (student_id , name , email, phone_num , points , parking_space_id)
    values ("${id}" , "${name}" , "${email}" , "${phone}", 0 , null)`
    mysqlcon.query(query, function (err, result) {
        if (err) throw err;
        res.send("added")
    });
})

app.get("/Get_ID/:email" , (req, res)=>{
    let email = req.params.email

    let query = `SELECT student_id from user_info WHERE email = "${email}"`
    mysqlcon.query(query, function (err, result) {
        if (err) throw err;
        res.send(result[0])
    });
})

app.get("/get_data/:email" , (req, res)=>{
    let email = req.params.email

    let query = `SELECT * from user_info WHERE email = "${email}"`
    mysqlcon.query(query, function (err, result) {
        if (err) throw err;
        res.send(result[0])
    });
})




app.listen(3000, () => {
    console.log(`Example app listening at http://localhost:${3000}`)  
})