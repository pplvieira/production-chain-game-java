# Production-Chain-Game devlog

#### 11/01/25 (3h)
- Planning and design with Notion and Chatgpt
- Java project setup. Duplicating many backend skeleton classes from the production chain interface

#### 12/01/25
- Work on ```ProductionBuildingType``` and ```BuildingInstance``` classes

#### 15/01/25
- Work on ```BuildingInstance``` and inheritance

(Missing a couple of days here)

#### 13/03/25 and 14/03/25 (2h)
- Work on ```BuildingType```, ```BuildingTypeList``` (list of building types of the same category) and reading and writing these lists to json.

#### 16/03/25 (2h)
- Work on `BuildingInstance` and a respective child for each building class.
- Json serialization for BuildingTypeList and BuildingInstance. Troubleshooting.

#### 17/03/25 (2h)
- Created `Storage` class, for resource handling in buildings. Has some control methods. Serialization and implementation setup. Building instances implement these, but storage buildings should go beyond on the control of what items are allowed and their durability inside. WILL DEVISE AN ADVANCED STORAGE CLASS THAT EXTENDS STORAGE with more control
- Work on `Recipe` and `RecipeList` (and classes implemented by them) classes. Json serialization for them. Most was already implemented but not documented or tested.
4
#### 18/03/25 (2h)
- `AdvancedStorage` class. Implements a queue of item batches that have a name and number of turns to expire. Items go bad every turn.
- Json Serialization for AdvancedStorage and BuildingInstance (that implements AdvancedStorage).

#### 19/03/25 (1h)
- Work on recipe system on ProductionBuildingInstance. Recipe execution, and check if they can be performed.

#### 25/03/25 (2h30)
- ``ItemRegistry`` and ``RecipeRegistry`` classes, mostly following chat suggestions. Serialization of those. Defined as final static lists of ItemTypes or Recipe, for read-only use encyclopedia of all items and recipes implemented.
- Item and Recipe categories to be read by buildings that can only produce some categories of recipes or storages that can only store some items or have diferencial degradation times for different item categories. (STORAGES NOT IMPLEMENTED YET) 

#### 26/03/25 (>2h)
- Work on `BuildingTypeList`, that works like a non static registry. Serialization and tests. Fixed some serialization issues with ItemRegistry.
- BuildingInstance only carries around a string with the BuildingType, that can later be retreived from BuildingTypeLists (registries). Serialization problems and tests on BuildingInstance.
- Prepare to write item, recipe and productionBuildingTypes json ROM files, in Main.java. 

#### 31/03/25 + 2/04/25 (Milestone 1.0)
- Research and planning with ChatGPT and Notion board. Planning next stages and setting up milestones
- Testing at Main.java and finalizing Milestone 1.0

#### 03/02/25 (2h)
- `AnimalTypes`, `AnimalInstances` and `AnimalRegistry`. Not 100% implemented.
- Java project organization

#### 06/04/25 (4h)
- ``AnimalBuildingType`` and `AnimalBuildingInstance`. Development of animal modelling system. Serialization and tests on other classes.
- Rework on the recipe system in building types and instances. With `recipeHandler` implemented by ALL BuildingTypes, and the recipe system is implemented on all BuildingInstances.