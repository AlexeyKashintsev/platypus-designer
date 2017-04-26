/**
 * 
 * @author ${user}
 * @stateless
 * @public 
 */
define('${appElementName}', ['orm', 'invoke'], function (Orm, Invoke, ModuleName) {
    function ${appElementName}() {
        var self = this, model = Orm.loadModel(ModuleName);

        self.implementMe = function(onSuccess, onFailure){
            Invoke.later(function(){ // Asynchronous work imitation
                onSuccess();
            });
        };
    }
    return ${appElementName};
});
