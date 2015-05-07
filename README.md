# Slick2D Eclipse Project "Perkele"
by 
Emil Holmen(eholme13@student.aau.dk)
Andreas Ryge(aryge13@student.aau.dk) 
Rasmus Bons(rbons13@student.aau.dk)

Welcome to our game Perkele! In this README-file you can read the description of the game we based Perkele on(Diablo 2) and a indepth description of Perkele itself.

Description of the Original Game (Diablo 2)
We took our inspiration from Blizzards classic fast-paced RPG; Diablo 2.
In Diablo, you assume the role as one of five different classes(seven with the expassion pack);Barbarian, Amazon, Paladin, Necromancer, Sorceror,(Assassin and Druid). You battle the minions of hell, which becomes increasingly harder. The minions and bosses varies in their behavior, their damage and their method of attacking you. As you fight through the minions and bosses, you increase in level and learn new skills and spells. All monster in the game has the possibility of dropping loot which will make you even stronger, by change your stats and increas your skills. The essence of the game is to achieve higher level and better loot, to be able to progress even further.
Diablo tilebased map and is viewed from an isometric view. The isometric view gives a 2D game the illusion of being a 3D game. 

As we could not implement the everything about Diablo, we decided to focus on implementing loot, which have different attributes and two kinds of enemy. Although we would have liked to implement isometric view, it was deemed too low priority and too hard to implement. 

Description of our Game (Perkele)
In Perkele you play as an unfornunate hero who had a run-in with the Finnish Devil, who took all your loot and left you (almost) naked with only a smelly stick to defend yourself. Now it is up to you to battle through the horde of dwarfs and elves(Perkele's allied), take their loot and take the fight to Perkele himself, to extract revenge for your missing loot!

What our game took and didn't take from the original

What we implemented: 
* Scaling Loot with different stats: The loot that drops from enemies is based on the enemy level, which in turn makes the enemy stronger, and Random Number Generator(RNG). So the stronger the enemy, the stronger the loot. Due to the RNG, two weapons that drops from the same level enemy will be different, both for the better or worse. In addition, melee weapon has a chance of a speciel attributes called Life Steal, which gives you an amount of hp depending on its value. So while one weapon might deal more damage than another, the other might be better due to the Life Steal ability. 

* Scaling enemies: As you progress through the waves of the enemies, they will become stronger, and thereby drop better loot. In Diablo, the further you get into the game, the harder enemies you will face and be rewarded with better loot.

* Boss: In Diablo each part of the story ends up with a showdown with a boss, which is an especially strong enemy with special abilities. In Perkele, a boss will spawn each 10th wave. He is bigger, faster, stronger and more durable than the other enemies, and he both got ranged and melee attack.

* Equipment: One of the main components of Diablo is the loot/equipment system. With weapons you deal more damage and with armor you receive less damage, in order for you to survive later on in the game. Perkele has the same feeling, as you much equip yourself with better loot to be able to kill higher level enemies. 


What we did not implement:  
* Skills: We did not have time to implement additional abilties beside melee and ranged attacks. So there is no special attack to e.g. kill several enemies at once, and enemies won't have any special attack.

* Save game: In Diablo, you save you characters stats, skills, equipment, loot and quest completed each time you exit the game. Since you can only have the items you have equiped and there is no skills or quest, the save game function was deemed to low a priority.

* Quests: The progression in Perkele is continious waves of enemies that become increasingly more and stronger. The quest would require a story, which would take way too much time 

* Classes: Since there is no skills and the only thing that can be altered is the weapon damage, armor and attack speed, there is no real use for player classes. More attributes and skills should be implemented before classes should be implemented

* Inventory: We started out with trying to implement an inventory, so you could carry several weapons and choose which one you would equip, but due to the simplicity of the game, and absence of breaks(In Diablo there were towns to rest and have a break), the inventory was removed.

What we added that was not in the original Game:
* Enemies can pick up loot: If an enemy touches a weapon or an armor that is better than their current damage/armor, they will pick it up an use it. This makes it possible for the enemies to change even after spawning, which makes them even more dangerous.

* You can destroy loot: To coutneract the enemies picking up weapon, the player can destroy items that lies on the ground. However, while attacking loot you cannot attack enemies meanwhile, so you have to priorties if you want to destroy loot, and maybe get hit, to avoid letting the enemies become stronger.

* Wave format: Rather than act, and areas in Diablo, where enemies spawn on fixed points. Perkele is in an arena where enemies will spawn randomly around the map. When you clear the map of enemies the next wave will spawn after 5 seconds, with more and stronger enemies.

##The Goal/Win condition
The goal is to keep fighting through waves upon waves of enemies. Each 10th wave there will be a Boss(Perkele), that is an extremly strong enemy with special abilities. There is no limit waves, so you will be able to continue(getting better loot, killing more enemies) until you get killed.

##The Lose Condition
Enemies damages you all depending on their level, their weapon and your armor. When you health pool reaches zero, you will die and the game ends. At the end screen you will be informed at which you died.

##How to play/controls

Your movement is due to the form of the map, two dimensional. You control your characters movement with 'W'(Up)  'A'(Left)   'S'(Down)    'D'(Right)

You have two kind of attacks, melee(displayed with a Sword) and ranged(displayed with a Bow). The attack is initialised by pressing the LEFT MOUSE BUTTON. You can swap between melee and ranged attack by pressing 'Q'.
The ranged attack will fire a missle in the direction of the mouse position, which damage any enemy that it collides with. The melee attack will damage any target if they are within range and THE MOUSE HOVER over the target.

When enemies dies, there is a chance for them to drop an item(melee weapon, ranged weapon or armor). By holding LEFT-SHIFT down, you can see the level and the name of all items on the screen. When the mouse hover over items while LEFT-SHIFT is pressed down, details about the item(Damage, attack speed, armor etc.) will be displayed.

By pressing LEFT-MOUSEBUTTON, while LEFT-SHIFT is down and you are within melee range, your character will pick the item up, replacing the new item with the currently equiped one. The older item will be placed on the ground.

However, you are not the only one who can use loot. If an enemy collides with an item, that is better than their current one, they will pick it up and thereby become more dangerous. 
Melee enemies will only pick up melee weapons and armor, while ranged enemies only will pick ranged weapon and armor.

To counter this effect, you have the possibility of destroying items when they are on the ground. By hover the mouse over items WITHOUT PRESSING LEFT-SHIFT, and pressing LEFT-MOUSEBUTTON, you attack the item. All items require two attacks before they are destroyed, no matter the level.
 
Overview of Controls:
Movement: WASD
Attacking: LEFT-MOUSEBUTTON
Swapping between melee and ranged attacks: Q
View loot names: LEFT-SHIFT
View loot details: LEFT-SHIFT+HOVER MOUSE OVER.
Pick up Loot: LEFT_SHIFT+HOVER MOUSE OVER+LEFT MOUSE BUTTON
