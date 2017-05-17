/**
 * @author ${user}
 * 
 * @public
 * @stateless
 * 
 * Note that Web Socket endpoint's module should have a solid name.
 * So, 3 argument define should be used or such module should be placed in the root folder - 'app'.
 */
define('${appElementName}', ['rpc', 'logger', 'orm', 'invoke'], function (Lpc, Logger, Orm, Invoke, ModuleName) {
    function ${appElementName}() {
        var self = this, model = Orm.loadModel(ModuleName);
        var accounter = new Lpc.Proxy('WsSessionsAccounter');

        this.onopen = function (session) {
            accounter.add(session.id, function (aData) {
                session.send(aData);
            });
        };
        this.onclose = function (evt) {
            // evt.id - Session id
            // evt.wasClean - True if session was closed without an error
            // evt.code - Session close code
            // evt.reason - Description of session close
            accounter.remove(evt.id);
        };
        this.onmessage = function (evt) {
            // evt.id - Session id
            // evt.data - Text data recieved from other (client) endpoint
            accounter.broadcast(evt.data);
        };
        this.onerror = function (evt) {
            // evt.id - Session id
            // evt.message - Error message from container's exception
        };
    }
    return ${appElementName};
});