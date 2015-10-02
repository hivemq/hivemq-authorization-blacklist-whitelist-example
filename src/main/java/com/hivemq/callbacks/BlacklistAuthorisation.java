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

package com.hivemq.callbacks;

import com.hivemq.spi.aop.cache.Cached;
import com.hivemq.spi.callback.CallbackPriority;
import com.hivemq.spi.callback.security.OnAuthorizationCallback;
import com.hivemq.spi.callback.security.authorization.AuthorizationBehaviour;
import com.hivemq.spi.security.ClientData;
import com.hivemq.spi.topic.MqttTopicPermission;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.hivemq.spi.topic.MqttTopicPermission.*;

/**
 * @author Lukas Brandl
 */
public class BlacklistAuthorisation implements OnAuthorizationCallback {

    /*
     * Subscriptions for topic "client/<client id>" with QoS 1, are allowed.
     * Subscriptions for all other topics that start with "client/" are denied.
     * Everything else is allowed.
     */
    @Override
    @Cached(timeToLive = 10, timeUnit = TimeUnit.SECONDS)
    public List<MqttTopicPermission> getPermissionsForClient(ClientData clientData) {

        final List<MqttTopicPermission> permissions = new ArrayList<>();
        permissions.add(new MqttTopicPermission("client/" + clientData.getClientId(), TYPE.ALLOW, QOS.ONE));
        permissions.add(new MqttTopicPermission("client/#", TYPE.DENY, ACTIVITY.SUBSCRIBE));

        return permissions;
    }

    @Override
    public AuthorizationBehaviour getDefaultBehaviour() {
        return AuthorizationBehaviour.ACCEPT;
    }

    @Override
    public int priority() {
        return CallbackPriority.MEDIUM;
    }
}
