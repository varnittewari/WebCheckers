/**
This Module Checks to see if a player has made a move. If they have
then refresh the page.
*/
define(function(require){
    'use strict';
    var SpectatorModeConstants = require('./SpectatorModeConstants');


    function ObservingState(controller,gameState) {
        // private attributes
        this._controller = controller;
        this._gameState = gameState;
    };

    /**
     * Get the name of this state.
     */
    ObservingState.prototype.getName = function getName() {
        return SpectatorModeConstants.OBSERVE_STATE;
    }

    ObservingState.prototype.onEntry = function onEntry() {
        setTimeout(() => { this._controller.setState(SpectatorModeConstants.CHECKING_GAME_STATE); }, 2000);
    }


    return ObservingState;

});
