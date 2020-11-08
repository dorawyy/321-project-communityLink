/* This module handles user requests
 * 
 * Functions:
 *   
 */




var db = require("../dbInterface/userDB.js");
var checkData = require("./userInfoCheck.js");

// module.exports.getServices = function (req, res) {
//   console.log("In service handler: get services");
//   console.log("query: " + JSON.stringify(req.query));

//   var conditions = reqData.getConditionsFromQuery(req.query);

//   console.log(conditions);

//   db.get(conditions, (services) => { 
//     res.json(services);
//   });
// }



module.exports.addUser = function (req, res) {
  // console.log("In service handler: add user");
  
  var user = req.body;

  if(!checkData.checkUserInfo(user)){
     throw "This is not a valid User Object.";
  }
  

  db.add(user, (username, err) => {
    if (err) {
      res.status(err.code).json(err);
    } else {
      res.json(username);
    }
  });
};



module.exports.deleteUser = function (req, res) {
  // console.log("In service handler: delete user");
  const username = req.query;
  const keys = Object.keys(username);
  if(keys.length!==1 || keys[0]!=="username"){
    throw "The delete username passed in was wrong.";
  }

  
  db.delete(username.username, (user, err) => {
    if (err) {
      res.status(err.code).json(err);
    } else {
      res.json(user);
    }
  });
 
};







module.exports.updateUser = function (req, res) {
  // console.log("In service handler: update service");
  var updateUser = req.body;

  if(!checkData.checkUserInfo(updateUser)){
    throw "This is not a valid User Object.";
 }

  db.update(updateUser, (user, err) => {
    if (err) {
      res.status(err.code).json(err);
    } else {
      res.json(user);
    }
  });

};




module.exports.loginCheck = function (req, res) {

  // console.log("In service handler: check user login. " + JSON.stringify(req.body));
  
  var loginInfo = req.body;
  
  db.loginCheck(loginInfo , (result, err) => {
    if (err) {
      res.status(err.code).json(err);
    } else {
      res.json(result);
    }
  });

};
