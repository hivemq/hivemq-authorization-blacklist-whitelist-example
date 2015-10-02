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

import static com.hivemq.spi.topic.MqttTopicPermission.TYPE.ALLOW;
import static com.hivemq.spi.topic.MqttTopicPermission.TYPE.DENY;

/**
 * @author Lukas Brandl
 */
public class NextAuthorisation implements OnAuthorizationCallback {

    /*
     * Topics starting with "debug/" are always allowed.
     * Topics starting with "system/" are always denied.
     * Otherwise the next callback decides.
     * If this is the only callback, all other topics are denied.
     */
    @Override
    @Cached(timeToLive = 10, timeUnit = TimeUnit.SECONDS)
    public List<MqttTopicPermission> getPermissionsForClient(ClientData clientData) {

        final List<MqttTopicPermission> permissions = new ArrayList<>();
        permissions.add(new MqttTopicPermission("debug/#", ALLOW));
        permissions.add(new MqttTopicPermission("system/#", DENY));

        return permissions;
    }

    @Override
    public AuthorizationBehaviour getDefaultBehaviour() {
        return AuthorizationBehaviour.NEXT;
    }

    @Override
    public int priority() {
        return CallbackPriority.HIGH;
    }
}
