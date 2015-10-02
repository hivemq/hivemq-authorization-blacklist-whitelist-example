/*
 * Copyright 2015 dc-square GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hivemq.plugin;

import com.hivemq.callbacks.BlacklistAuthorisation;
import com.hivemq.callbacks.NextAuthorisation;
import com.hivemq.callbacks.WhitelistAuthorisation;
import com.hivemq.spi.PluginEntryPoint;
import com.hivemq.spi.callback.registry.CallbackRegistry;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Lukas Brandl
 */
public class BlackAndWhitelistExampleMainClass extends PluginEntryPoint {

    private final WhitelistAuthorisation whitelistAuthorisation;
    private final BlacklistAuthorisation blacklistAuthorisation;
    private final NextAuthorisation nextAuthorisation;

    @Inject
    public BlackAndWhitelistExampleMainClass(final WhitelistAuthorisation whitelistAuthorisation,
                                             final BlacklistAuthorisation blacklistAuthorisation, NextAuthorisation nextAuthorisation) {

        this.whitelistAuthorisation = whitelistAuthorisation;
        this.blacklistAuthorisation = blacklistAuthorisation;
        this.nextAuthorisation = nextAuthorisation;
    }

    @PostConstruct
    public void postConstruct() {

        CallbackRegistry callbackRegistry = getCallbackRegistry();
        callbackRegistry.addCallback(nextAuthorisation);
        callbackRegistry.addCallback(blacklistAuthorisation); //Register blacklist or whitelist authorisation here
    }

}
