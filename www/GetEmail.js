var exec = require('cordova/exec');

exports.getEmail = function(success, error) {
    exec(success, error, "GetEmail", "getEmail", []);
};
