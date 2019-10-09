package eu.thesimplecloud.lib.config

interface IConfigLoader<T : IConfig> {

    /**
     * Load the config form the file.
     * If the config does not exist it returns a default value without creating the file.
     */
    fun loadConfig(): T

    /**
     * Saves the object to the config file
     */
    fun saveConfig(value: T)

    /**
     * Returns whether the config file exist
     */
    fun doesConfigFileExist(): Boolean

}