/**
 * 
 * @author ${user}
 * @stateless
 * @public
 */
define('${appElementName}', ['orm', 'invoke'], function (Orm, Invoke, ModuleName) {
    function ${appElementName}() {
        var self = this, model = Orm.loadModel(ModuleName);

        /**
         * @get /albums
         */
        self.implementMe = function(uriTail, onSuccess, onFailure){
            Invoke.later(function(){ // Asynchronous work imitation
                onSuccess([
                    {
                        name: 'photos',
                        date: new Date()
                    },
                    {
                        name: 'moments',
                        date: new Date()
                    }
                ]);
            });
        };
    }
    return ${appElementName};
});
