package eu.thesimplecloud.base.manager.network.packets.player

import eu.thesimplecloud.base.manager.events.CloudPlayerLoginEvent
import eu.thesimplecloud.base.manager.startup.Manager
import eu.thesimplecloud.clientserverapi.lib.connection.IConnection
import eu.thesimplecloud.clientserverapi.lib.packet.packettype.JsonPacket
import eu.thesimplecloud.clientserverapi.lib.promise.ICommunicationPromise
import eu.thesimplecloud.lib.CloudLib
import eu.thesimplecloud.lib.player.CloudPlayer
import eu.thesimplecloud.lib.player.OfflineCloudPlayer
import eu.thesimplecloud.lib.player.connection.DefaultPlayerConnection

class PacketInCreateCloudPlayer() : JsonPacket() {

    override suspend fun handle(connection: IConnection): ICommunicationPromise<out Any> {
        val playerConnection = this.jsonData.getObject("playerConnection", DefaultPlayerConnection::class.java)
                ?: return contentException("playerConnection")
        val proxyName = this.jsonData.getString("proxyName") ?: return contentException("proxyName")
        val offlinePlayer = Manager.instance.offlineCloudPlayerLoader.getOfflinePlayer(playerConnection.getUniqueId())
        val cloudPlayer = if (offlinePlayer == null) {
            CloudPlayer(playerConnection.getName(), playerConnection.getUniqueId(), System.currentTimeMillis(), System.currentTimeMillis(), 0L, proxyName, null, playerConnection)
        } else {
            CloudPlayer(playerConnection.getName(), playerConnection.getUniqueId(), offlinePlayer.getFirstLogin(), System.currentTimeMillis(), offlinePlayer.getOnlineTime(), proxyName, null, playerConnection)
        }
        CloudLib.instance.getCloudPlayerManager().updateCloudPlayer(cloudPlayer)
        Manager.instance.offlineCloudPlayerLoader.saveCloudPlayer(cloudPlayer.toOfflinePlayer() as OfflineCloudPlayer)
        return unit()
    }
}