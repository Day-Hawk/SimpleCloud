package eu.thesimplecloud.base.manager.commands

import eu.thesimplecloud.launcher.console.command.ICommandHandler
import eu.thesimplecloud.launcher.console.command.ICommandSender
import eu.thesimplecloud.launcher.console.command.annotations.Command
import eu.thesimplecloud.launcher.console.command.annotations.CommandArgument
import eu.thesimplecloud.launcher.console.command.annotations.CommandSubPath
import eu.thesimplecloud.lib.CloudLib

@Command("info", false)
class InfoCommand : ICommandHandler {

    @CommandSubPath("wrapper <name>", "Prints some information about the specified wrapper")
    fun wrapper(commandSender: ICommandSender, @CommandArgument("name") name: String) {
        val wrapper = CloudLib.instance.getWrapperManager().getWrapperByName(name)
        if (wrapper == null) {
            commandSender.sendMessage("manager.command.info.wrapper.not-exist", "The specified wrapper does not exist.")
            return
        }
        commandSender.sendMessage(wrapper.toString())
    }


    @CommandSubPath("service <name>", "Prints some information about the specified service")
    fun service(commandSender: ICommandSender, @CommandArgument("name") name: String) {
        val service = CloudLib.instance.getCloudServiceManger().getCloudServiceByName(name)
        if (service == null) {
            commandSender.sendMessage("manager.command.info.service.not-exist", "The specified wrapper does not exist.")
            return
        }
        commandSender.sendMessage(service.toString())
    }

    @CommandSubPath("group <name>", "Prints some information about the specified group")
    fun group(commandSender: ICommandSender, @CommandArgument("name") name: String) {
        val group = CloudLib.instance.getCloudServiceGroupManager().getServiceGroupByName(name)
        if (group == null) {
            commandSender.sendMessage("manager.command.info.group.not-exist", "The specified wrapper does not exist.")
            return
        }
        commandSender.sendMessage(group.toString())
    }


}