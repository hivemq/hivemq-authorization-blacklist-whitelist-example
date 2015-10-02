package com.hivemq.callbacks;

import com.hivemq.spi.security.ClientData;
import com.hivemq.spi.topic.MqttTopicPermission;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class BlacklistAuthorisationTest {

    @Mock
    ClientData clientData;

    @Before
    public void setUp() {

    }

    @Test
    public void test_get_permission() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(clientData.getClientId()).thenReturn("clientId");
        BlacklistAuthorisation blacklistAuthorisation = new BlacklistAuthorisation();

        final List<MqttTopicPermission> permissionsForClient = blacklistAuthorisation.getPermissionsForClient(clientData);

        assertEquals(2, permissionsForClient.size());
        assertEquals("client/clientId", permissionsForClient.get(0).getTopic());
    }
}