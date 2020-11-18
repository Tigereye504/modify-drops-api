# Modify Drops Api
Allows for more complex and context-sensitive item drops. An API created for use in Chest Cavity and Hellish Materials, but is valuable to anyone who want to have greater control over the output of loot tables

## How it Works
Whenever loot is generated from a loot table (even if the result is no drops), Modify Drops API will call the following events in order: GenerateLootCallbackAddLoot, GenerateLootCallbackModifyLoot, and GenerateLootCallbackAddUnmodifiableLoot.

GenerateLootCallbackAddLoot listeners accept the loot table's LootContextType and LootContext, and return a List<Itemstack>. Anything in the returned list is added to the existing list of drops. GenerateEntity and GenerateBlockLootCallbackAddLoot are called if the LootContextType is ENTITY or BLOCK, and so can be used when adding drops to those loot events instead of checking the LootContextType yourself.

GenerateLootCallbackModifyLoot listerners accept the loot table's LootContextType, LootContext, and the List<Itemstack> of drops. They return a List<Itemstack> which will replace the old loot. ModifyLoot, like AddLoot, calls Enity and Block sub-events for ease of use.

GenerateLootCallbackAddUnmodifiableLoot functions identically to AddLoot, but is called after ModifyLoot and so is for drops that you do not want other listeners to interact with.

## Importing Modify Drops API

To use Modify Drops API in your mod, add the following line to your dependencies in build.gradle:
```
modApi "com.github.tigereye504:modify-drops-api:x.x.x"
```
Optionally, you can embed Modify Drops API as a jar-in-jar by also including the following in your dependencies:
```
include "com.github.tigereye504:modify-drops-api:x.x.x"
```
where x.x.x is the version of the API you wish to use.
