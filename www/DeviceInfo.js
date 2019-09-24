var exec = require('cordova/exec');

module.exports.getImei = function (arg0, success, error) {
    exec(success, error, 'DeviceInfo', 'getImei', [arg0]);
}
