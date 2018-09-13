/**
This Module Checks to see if a player has made a move. If they have
then refresh the page.
*/
define(function(require){
  'use strict';
  var SpectatorModeConstants = require('./SpectatorModeConstants');
  var AjaxUtils = require('../../util/AjaxUtils');


  function CheckForFinishTurnState(controller,gameState) {
    // private attributes
    this._controller = controller;
    this._gameState = gameState;

  };


  /**
   * Get the symbolic name this state.
   */
  CheckForFinishTurnState.prototype.getName = function getName() {
    return SpectatorModeConstants.CHECKING_GAME_STATE;
  };


  CheckForFinishTurnState.prototype.onEntry = function onEntry() {
    //Checking for difference in BoardView
    jQuery.post('/checkForChange', '')
    // HTTP success handler
    .done(handleResponse.bind(this))
    // HTTP error handler
    .fail(AjaxUtils.handleErrorResponse)
    // always display a message that the Ajax call has completed.
    .always(() => console.debug('SubmitTurn response complete.'));

    // helper function (Ajax success callback)
    function handleResponse(message) {
      if (message.type === 'info') {
        if (message.text === 'true') {
          // tell the browser to redisplay the Game View to get the updated board
          window.location = '/spectate';
        } else {
          this._controller.setState(SpectatorModeConstants.OBSERVING_STATE);
        }
      }
      // handle error message
      else {
        // There are valid error conditions, such as not completing
        // a jump sequence.
        this._controller.displayMessage(message);
      }
    }
  }


  return CheckForFinishTurnState;

});
