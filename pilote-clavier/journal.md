# Journal

## le 2/2/23

Tout le monde est là.

### Fait

Alexandre : Fait une démo sous Swing qui récupère les codes ASCII les multiplie par 2 et les affiche.

Les autres : recherche documentaire.

### Discuté en séance

type des événements

### Faire

- cas d'usage
- scenarii (séquences d'événements)
- écrire classe pour évenements
- proposer une interface (provisoire) pour les modules
- commencer à developer un vrai module (utilisant cette interface), écrire ses tests tout de suite

## 9/2/23

Tout le monde est là.

Quelques dans la démo, mais pas grand chose sur ce qui sera le code définitif (rien dans les objectifs prévus).

J'ai expliqué l'exemple des layers avec plus de détails pour montrer ce qu'on attendait.

À faire :

- premier jet des layers
- + objectifs de la semaine d'avant (remarque : scénario de bug évoqué dans le cas des layers)

 
## 16/2/23

Fait :

  - Alexandre : architecture/types
  - Jana : layers (map, etc.), demo où la touche Ctrl change de layer
  - Elisa : expérimentation mise en minuscule/majuscule 
  - NavDeep : gestion des touches non définies dans un layer
  - Laure : fonction `accept` : comment faire pour que ça fonctionne en retournant `void` (réponse en séance de suivi: le dernier module de la chaine modifie lui-même la textarea)


À faire :

- prendre en compte les remarques ci-dessous
- faire le "pipeline" minimal respectant l'architecture suggérée : entrée depuis KeyListener > transformation via layers (LayerTranslate) > prise en compte des touches de changement de layer (LayerSwitch) > sortie vers TextArea
- commencer autre module ?


Remarques :

- nom des packages (ASCII, DEV)
- attention aux imports non utilisés
- layers : initialisation en dur dans initLayers -> remplacer par initialisation depuis le main en appelant des méthodes de Layer
- .idea versionné

Suggestions :

- utiliser classe Robot comme alternative à Swing (et aux appel système) pour la sortie

## 23/2

Fait :

- Alexandre : packages renommés (mais c'est encore sur une branche locale)
- Alexandre : keymap json (pas encore parsé car n'arrive pas à trouver le fichier depuis java)

(Pas de travail des autres membres cette semaine !)

À faire :

- regarder resourceLoader
- continuer les objectifs prévus la semaine précédente

## 9/3

Fait :
- Alexandre : keymap json fonctionnelle
- Laure/Jana/Navdeep/Elisa : branche restructuration
    - implémentation du module determinant les changements de layer -> l'initialiser avec un fichier json
    - restructuration du code selon le concept getNextModule()
    - recherche sur la classe robot qui effectuera l'action finale pour la sortie
- Jana : fonction qui va chercher dans les layers en dessous si la key n'est pas définie dans le current

Remarques/Questions à poser :
- doutes sur l'endroit où doivent se trouver et être initialisées la "liste" des modules et la liste des events
- questions sur l'implémentation des autres modules (doit-on implémenter des cas précis ou est-ce l'utilisateur qui choisira,
                                                   peut on utliser les mod key MT(...,...)
                                                     ... )

À faire :

 - JSON : autoriser aliases plus parlants que des keycodes sous forme d'entiers
 - Module de sortie utilisant robot
 - Gérer les touches de changement de layer comme une entrée particulière dans le JSON (sous-objet avec un attribut pour le layer cible)
 - commencer hold-tap ?
 - 

## 16/03

- Alexandre : créer un manuel qui associe à une touche un nom son keycode et son carractère en json , robot qui fonctionne les fonctions associe (pas reussit a commit)
- Laure : changement de ConsumerModule en interface initialisation de la liste de module avec un getter dans chaque classe (il y a t-il un autre moyen moins redondant) 
- Jana : lien entre tous les modules jusqu'au module final, essai d'utilisation de la classe robot (où faut t il initialiser la liste des modules ?)
- Elisa : adapter le json actuel au nouveau manuel, ajout d'un layer de caractere speciaux basé sur le modele de Miryoku (il faut completer donc le manuel et l'adapter)
- Navdeep: (branche holdTap) création de la classe HoldTap


Question :

- Comment commit avec l'erreur ?
    -> repartir d'un nouveau clone
- comment faire pour parcourir à chaque fois le manuel et init les layers via ce manuel sans faire 5 bouvle for ? 
    -> utiliser la méthode get de Map (et pas besoin d'une liste de liste de map...)
- Comment bien faire le parcours de layers ? 
    -> idem
- Lorsqu'un holdTap est activé on lui associe une liste pour avec les touches qu'elle modifie avec l'effet (fichier json)? Ou bien nous faisons en sorte que le comportement de cette touche soit remplacée par l'effet de l'autre touche(class Mod Tap?)?
- Plusieurs modificateurs en même temps?
    -> maintenir tableau des modificateurs actifs (boolean[]), le tableau est envoyé avec chaque événement

À faire : finir ce qui est commencé

## 23/03

### Pré-rempli par les étudiants

- Elisa : liste d'évènements "isReleasedList" dans laquelle on add les events (à true) et les set à false quand ils sont relachés. Test temporaire: modification de executeAction(Event e) pour vérifier que cela nous montre si la l'event est true ou false
- Laure : Prise en compte des remarques chaque module admet un suivant
- Alexandre : Modification et implemntation des fonction initlayers et initdictionnaire en hashmap dans la classe layers pour faciliter la recherche dans le dictionnaire
- Navdeep: changements de la classe HoldTap, prise en compte des remarques
- Jana: ajustements des changements de layers, clavier qwerty (nb : recreer le clavier initial de base)

Question :

- Problème avec la construction de released, faire un nouvel evenements ou bien changer un boolean is pressed mais ensuite nous allons devoir a chaque fois parcourir a chaque fois toute la liste devent?
- Test de la liste isReleasedList et des booleens, les failles :  => affiche "truetrue" au premier clique et c'est uniquement à partir du second qu'il affiche correctement "truefalse" comme attendu. Comment rectifier ce premier clique ?
                                                                 => affiche bien "trutruetrue" n-fois quand on reste appuyé (à adapter à holdtap) mais si l'on relache la touche puis refaisons la même action, on a "truefalsetruefalse" n-fois. Voir comment remedier à cela pour avoir uniquement des true.
- GoTolayer: Iterator it,  type generique, quelle ecriture garder pour la suite ?  
- 

### Dans le chat, explication sur hold-tap :

Quand on reçoit un événement :

- si c'est un press de hold-tap -> on le met dans la liste des décisions à prendre
- si c'est une autre touche, s'il n'y a pas de décision à prendre, on transmet; sinon on met en attente, si de plus c'est un release de hold-tap en cours, ou qu'on est après délai, on prend une décision pour le hold-tap (modifié)

Prise de décision :

- on transmet un press du hold ou tap de la décision à prendre
- on traite les touches qui étaient retenues
- si la touche traitée est un release d'une touche hold-tap dont on a envoyé le press, il faut absolument envoyer le release correspondant (release du comportement tap si on a envoyé un press du tap, release du comportement hold, sinon)

### Remarques

- sortir la lecture de config (JSON) dans sa propre classe qui fournit la configuration à tous les modules
- les événements devraient contenir non pas un KeyCode mais un Key. Un Key peut être soit un PhysicalKey (avec KeyCode/int) soit un HoldTapKey (avec Key tap, Key hold, long timeout, HoldTapMode mode). Dans les event, on peut aussi ajouter le physical key d'origine (ce qui permet de réinterpréter la touche si elle avait été retenue par HoldTap et renvoyée à Layers)
- de même les éléments contenus dans les différents layers de la keymap sont aussi des Key

### À faire

Prendre en compte ce qui a été dit pour finir les tâches en cours (ne pas hésiter à créer de nouvelles tâches plus petites).

## 30/03

Fait :

- Jana : organisation des classes key
- Autres : continuation des tâches de la semaine précédente.

À faire :

- terminer les tâches en cours (plus grand chose à faire sur layers et sur le chargement de config normalement)
- prendre en compte les conseils, notamment pour programmeer HoldTap
- regarder [https://gaufre.informatique.univ-paris-diderot.fr/adegorre/keyboard-tools] et programmer un module d'entrée basé sur evdev à la place de Swing.

## 06/03

Fait :

- Navdeep: changements dans la classe holdTap par rapport aux consseils donnés, differenciation des events selon si oui ou non ils sont des events de holdTap
- Elisa: modification de KeyRealeased pour l'adapter correctement au reste et changements dans holdtap par rapport aux conseils donnés
- Alexandre : correction de bug au niveau de l'affichage et d'erreur lié au module
- 

## 30/03

(la séance a eu lieu normalement, mais on s'est laissés prendre par le temps!)

## 6/04

Journée de mobilisation.

## 13/04

Journée de mobilisation.

Fait :

- Alexandre : transformations de tous les entiers en une clé "KeyLayers" et ajout d'une fonction pour initialiser dans le fichier JSON les changements de layers.
- Navdeep: envoie des events de la classe holttap au premier module si celui ci nest pas affecté par un hold


Questions :

- Comment optimiser les appels aux modules ?
  -> est-ce que c'était le problème du module qui était ré-instancié à chaque appel à getModule? Dans ce cas, il faut en effet absolument changer cela ! Le module suivant est créé et enregistré une fois pour toutes dans un attribut du module courant lors de la phase d'initialisation du pilote.
- Création d'un premier module qui gère les événements "e.consume" ? -> ???
- Le layer 0 peut être celui de base avec seulement les modificateurs de layers, donc on pourrait seulement ne pas "e.consume" l'événement de base.
  -> la question était si on pouvait considérer que par défaut on regarde le layer d'en dessus, et si rien n'est défini même sur le layer de base on retourne la touche qui est normalement à cet emplacement du clavier, la réponse est oui !
- Est-ce que la classe "Robot" sera utile avec l'implémentation système ? 
  -> non c'est juste une alternative. Mais c'est intéressant de s'assurer qu'elle continue à fonctionner. Disposer d'implémentation multiples est bon pour tester la robustesse du reste du code.


## 20/04

Alexandre absent

Fait :

- rien sur git (mais semaine d'examens en maths)
- réflexion sur double-tap (avertissement : il vaut mieux terminer le reste, à ce stade du projet)

À faire :

- DummyInput (module d'entrée créant des événements à partir de listes d'entrées horodatées) et DummyOutput (module de sortie consommant de événements et créant une liste de sorties horodatées)
- écrire un certain nombre de listes d'entrées et les listes de sorties correspondantes attendues, vérifier que c'est bien ce qui est produit en vrai (permet de tester Layers et HoldTap)
- dans les event, changer `int keykcode` par `Key key`
- faire `instanceof HoldTapKey` dans le `accept` de `HoldTap` pour savoir si on a bien affaire à une touche de hold-tap et non une autre touche (sans traitement particulier dans ce module)
- installer/configurer/essayer kmonad et kanata pour voir à quoi s'attendre avec les holdtap (tap-hold dans ces logiciels)
