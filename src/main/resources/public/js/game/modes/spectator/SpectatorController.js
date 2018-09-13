/**
 * This module exports the SpectatorController class constructor.
 *
 * This component does...
 */
define(function(require){
  'use strict';

  // imports
  var StatePatternMixin = require('../../util/StatePatternMixin');
  var ControlsToolbarMixin = require('../../util/ControlsToolbarMixin');
  var SpectatorModeConstants = require('./SpectatorModeConstants');

  // import SPECTATOR mode states
  var SpectatorModeStartState = require('./SpectatorModeStartState');
  var CheckForFinishTurnState = require('./CheckForFinishTurnState');
  var ObservingState = require('./ObservingState')

  /**
   * Constructor function.
   */
  function SpectatorController(view, gameState) {
    // Add the StatePattern mixin
    StatePatternMixin.call(this);

    // create states and a lookup map
    this.addStateDefinition(SpectatorModeConstants.SPECTATOR_MODE_STARTING,
            new SpectatorModeStartState(this, view, gameState));

    this.addStateDefinition(SpectatorModeConstants.CHECKING_GAME_STATE,
            new CheckForFinishTurnState(this, gameState));

    this.addStateDefinition(SpectatorModeConstants.OBSERVING_STATE,
            new ObservingState(this, gameState));


    // Add the ModeControls mixin
    ControlsToolbarMixin.call(this);
    // TODO: create mode control buttons ( switch perspective? )

    // Public (internal) methods

    /**
     * Start Spectator mode.
     */
    this.startup = function startup() {
      // start Spectator mode
      this.setState(SpectatorModeConstants.SPECTATOR_MODE_STARTING);
    }

  };

  // export class constructor
  return SpectatorController;

});
