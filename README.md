# SimpleJetpacks
A 1.16+ Paper plugin that adds Jetpacks to Minecraft

### Jetpacks
Jetpacks are defined in `config.yml`, in which you can specify the following:
* Base Chestplate - The base chestplate (iron, diamond, etc.)
* Fuel Capacity - The amount of fuel this type can hold
* Burn Rate - How much fuel is used per tick in flight
* Speed - The speed at which it moves you
* Particles - The particles that spawn while you fly
* Sound - The sound that plays while you fly
* Recipe - The crafting recipe

Jetpacks can not be enchanted with Unbreaking or Mending

To use a jetpack, hold shift while in midair.
  
Changing the values for a jetpack type will **not** update pre-existing jetpacks!
### Fuel
A jetpack's fuel is represented in the durability bar. (Don't worry, the jetpack will never actually break)

Right clicking with a fuel item (also defined in config.yml) in hand - or another jetpack - while wearing a jetpack adds fuel.  

The default fuel items are Coal (100 fuel), Charcoal (100), Redstone Dust (50), and Redstone Blocks (500)
### Commands
* `simplejetpacks give <player> <type>` - Gives the specified player a jetpack of the specified type. Requires the `simplejetpacks.give` permission
* `simplejetpacks reload` - Reloads the config file, requires `simplejetpacks.reload`. This will not update the jetpack types, that requires a restart.

### A Note on the File Size
This plugin uses some Kotlin, which requires some dependencies which boost the file size from ~30kb to ~1.3mb

## NOTE: This is my second-ever plugin, it may cause lag and have a bunch of bugs. I probably did not use many best practices. If you find an issue or have a suggestion, please let me know!
