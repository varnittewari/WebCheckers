/**
 * This module exports the SpectatorModeStartState class constructor.
 *
 * This component is an concrete implementation of a state
 * for the Game view; this state represents the starting state
 * of the Spectator mode.
 *
 * This is an exercise for the student.
 */
define(function(require){
  'use strict';

  // imports
  var SpectatorModeConstants = require('./SpectatorModeConstants');

  /**
   * Constructor function.
   *
   * @param {SpectatorController} controller
   *    The Spectator mode controller object.
   */
  function SpectatorModeStartState(controller, view, gameState) {
    // private attributes
    this._controller = controller;
    this._view = view;
    this._gameState = gameState;

  };

  //
  // Public (external) methods
  //

  /**
   * Get the symbolic name this state.
   */
  SpectatorModeStartState.prototype.getName = function getName() {
    return SpectatorModeConstants.SPECTATOR_MODE_STARTING;
  };

  /**
   * Method when entering this state.
   *
   * Build and begin the Spectator view mode.
   */
  SpectatorModeStartState.prototype.onEntry = function onEntry() {
    /*
    var currentPlayer = this._gameState.getCurrentPlayer();
    if ( currentPlayer !== this._gameState.getRedPlayer()
      && currentPlayer !== this._gameState.getWhitePlayer() ) {
      alert('SpectatorModeStartState invalid state variables.');
      return;
    }
    */

    jQuery.post('gameOver', '')
    // HTTP success handler
    .done(handleResponseA.bind(this))
    // HTTP error handler
    .fail(AjaxUtils.handleErrorResponse)
    // always display a message that the Ajax call has completed.
    .always(() => console.debug('CheckTurn response complete.'));

    function handleResponseA(message) {
        if (message.text !== 'false') {
            this._view.setHelperText(message.text);
        }
        else{
            this._initializeView();
            this._controller.setState(SpectatorModeConstants.OBSERVING_STATE);
        }
    }
  };


  /**
   * Initializes the content in the game-info fieldset.
   */
  SpectatorModeStartState.prototype._initializeView = function _initializeView() {
    // Create helper text
    var redPlayer = this._gameState.getRedPlayer();
    var whitePlayer = this._gameState.getWhitePlayer();
    var helperText = 'You are spectating a game of checkers between ' + redPlayer + ' and ' + whitePlayer
    + '. <br/><br/>';
    if(this._gameState.isRedsTurn()){
      helperText += "It's " + redPlayer + " turn.  The page will refresh periodically.";
    }
    else{
      helperText += "It's " + whitePlayer + " turn.  The page will refresh periodically.";
    }
    this._view.setHelperText(helperText);
  };


  // export class constructor
  return SpectatorModeStartState;

});
