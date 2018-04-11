# Documento de diseño de Dryland: Kingdoms Across

## Introducción
Dryland: Kingdoms Across es un proyecto de videojuego de carácter indie, cuyo equipo de desarrollo incluye a Marino Tejedor, Alvaro de las Heras, David Valdevira y Adrian Sánchez.

El videojuego está orientado a ser calificado para la asignatura de Tecnología de Videojuegos en la UAH. A pesar de ello, el propósito como el de todo videojuego es de ser entretenido de diferentes formas.

![Logo videojuego](/Artwork/Desierto_logo_v2.png)

La mecánica que vamos a detallar después es simple pero se ha demostrado en titulos similares que es divertida, y la historia está pensada para tener un transfondo y hacer pensar al jugador, y no sólo dar contexto a la acción.

La idea principal es simple y concisa, queremos un juego entretenido con una historia simple, pero sólida y con la que sea fácil empatizar con el protagonista. Vamos a huir en este sentido de dobles sentidos, drama o mensajes filosóficos puesto que no vamos a poder darle forma como se merece y nos gustaría.
 
El vector principal con el que transmitiremos todo esto será la narración de la historia entre la batalla, los diálogos, o los pensamientos del personaje, pues en el apartado gráfico o sonoro vamos a usar mayoritariamente recursos gratuitos de Internet. Una vez explicado esto, pasaremos al detalle en cada uno de los apartados.
 
 ---
 ## Historia del videojuego
 
El videojuego se sitúa en un contexto de fantasía con cierto aire medieval. Nuestro mundo jugable se compone de dos reinos, el de Auria y el de Rozio, separados por un desierto enorme en el centro, el Dryland, que los separa. Cruzar el Dryland y vivir para contarlo ha sido hasta el momento tarea imposible, razón por la cual el reino de Rozio, caracterizado por su imperialismo, no ha conseguido invadir al de Auria. Este desierto se generó en una gran guerra hace mucho tiempo, en la que los antepasados de las civilizaciones actuales dejaron únicamente algunas ruinas y templos como legado de su tecnología. Para descubrir qué secretos albergan, se destinan allí exploradores en misiones oficiales de palacio, pero no ha habido demasiado progreso porque están protegidas, y su interior cambia totalmente al salir y volver a entrar.

En este mundo, Solace, nuestro protagonista, es un explorador que va a recorrer una mina abandonada, que se sabe que tiene tecnología de los ancestros. Junto con sus compañeros se adentra y recorre hasta donde le fue encomendado. Lo que descubre sin embargo, poco más lejos del final de su misión, es lo que parece un portal. Intrigado se acerca, pero es sorprendido por otro explorador de su comitiva, Cillop, que no consigue asesinarle por la espalda. Lo único que Solace ve es una marca que le resulta familiar en su brazo, y no es consciente de la amenaza sobre su vida. Al volver a la superficie Cillop acusa a nuestro protagonista de poner en riesgo al reino, al propasarse en su cometido. En un juicio bastante injusto somos desterrados del reino.

Abandonado en el Dryland, Solace empieza a andar sin mucha esperanza, hasta que al borde de la muerte prácticamente se encuentra una pirámide. Entra, y tras recuperar fuerzas en un pozo que hay al entrar, se dispone a ascender por ella. Esta plagada de trampas, pero halla su camino hacia la cima gracias a anotaciones que se encuentra talladas por gente que perdió la vida allí y decidió ayudar al siguiente que viniese tras él. Descubre un portal que hay en la cima, y lo cruza, pensando que va a acabar en su reino otra vez.

Llega a una templo, pero es diferente al que él conoce. Lo atraviesa a fin de salir, necesitado de alimento y cuidado médico, dándose cuenta de que lleva la misma marca en el brazo que Cillop, que no es otra que la marca que hay inscrita en alguna pared de las ruinas de los ancestros. Despierta en una casa tras perder el conocimiento, en la casa de alguien que cruzó el portal antes que el hace ya unos años, que tiene la misma marca, y que vive en Rozio de incógnito. Le cuenta a nuestro protagonista que en este reino han descubierto un portal que lo comunica con la tierra natal de ambos, y que ha llegado a oídos del rey de Rozio. Han comprobado que funciona y tienen hasta espías, de lo que deduces que Cillop era uno de ellos, por lo que su siguiente paso en el plan es cruzarlo con un pequeño ejército y con la ayuda de los espías invadir Auria. 

Es imperativo cruzar antes que ellos y avisar de esto al otro lado, por lo que Syngrid les lleva a la mina en el lado de Rozio y se aventuran dentro para buscar el portal que comunica con su contrario. Lo encuentra y cruzan, pero descubren que por la aleatoriedad de la tecnología de los ancestros el ejército enemigo está por delante suya a pesar de haber entrado más tarde en la mina. De aquí en adelante Solace y Syngrid se adelantan hasta el palacio para unirse a la lucha y parar el ataque.

---
## Look and feel

Dryland: Kingdoms Across está diseñado para ser un videojuego en perspectiva aérea, con camára fija, con nuestro protagonista moviendose por mazmorras en un mundo 2D. La perspectiva que usaremos será ortogonal, aunque nuestro personaje no estará limitado a la cuadricula sino que deambulará en libre movimiento y en libre ángulo, con el control del teclado, sobre el plano 2D que hemos indicado.

Todo el arte incorporado, los entornos, menús, personajes y enemigos son del estilo Pixel Art, en su mayoría a 16bits escalados en una escala x3, con un estilo similar a este:


![Logo videojuego](/Artwork/arte-concept.png)

Buscamos un estilo elegante y sobrio, con detalles minimalistas y sin recargar. Para potenciar el efecto artístico del videojuego, vamos a intentar si está en nuestro plazo de tiempo además añadir efectos, sombras e iluminación híbrida, es decir, en toda la resolución posible, con degradados limpios y suaves que contrasten con el estilo pixel art del resto del juego.

Los tiles usados serán principalmente los mismos, modificados para nuestra ocasión en los detalles que necesitemos, dando diferentes colores dependiendo de si estamos en diferentes mazmorras, etc. Algún escenario será de exterior, pero la idea principal es que sea para contar alguna historia y que estos escenarios no formen parte del gameplay. Como detalle adicional y para dar realismo gráfico, contemplamos la idea de añadir paralelaje a nuestro render con un fondo que se mueva a velocidad diferente de la cámara para aparentar un falso 3D, al estilo Disney en sus películas de animación por capas.

En el apartado sonoro vamos a incluir música de 8 bits principalmente, del conocido artista Kevin MacLeod, que compone bandas sonoras y piezas musicales libres de royalties con una licencia CreativeCommons (es necesario indicarlo los nombres de las pistas en los créditos). De esta forma vamos a conseguir una integración total con el entorno PixelArt sin descuidar la calidad de la banda sonora. Temas para la intro del juego, para créditos o similares no van a ser necesariamente de 8 bits, estos temas los reservamos para el combate inmersivo.

---
## Mazmorras

En esencia nuestro juego es un dungeon crawler. Cada acto consistirá en uno o varios mapas de mazmorras, con diferentes salas unidas por pasillos, con un tamaño y tipo específico de salas dependiendo del acto en el que estemos. Tendremos que recorrer las mazmorras para perseguir un objetivo específico, que puede estar cerca o muy lejos del punto de partida. Para ello, generaremos de forma procedural la mazmorra de acuerdo a unos parámetros cada vez que entremos en un acto nuevo, ya sea por avance en la historia o por perder la partida.

Esto tiene una justificación desde un punto de vista de la historia, como hemos visto las ruinas que nos dejan nuestros antepasados están protegidas por una tecnología desconocida que hace que cada vez que entremos y salgamos las ruinas sean diferentes.

---
## Mecánicas y control

La mecánica de este videojuego no es algo radicalmente novedoso, sino que ya es jugable (o versiones parecidas) en títulos en el mercado actual, algunos con bastante éxito. La idea es movernos en un plano 2D libremente, a través de un mapa tileado, recorriendo una mazmorra para encontrar el objetivo especificado, la mayoría de las veces atravesando salas con bosses, o salas con enemigos menores. El modo de combate que esperamos implementar es, o bien a distancia, disparando en la dirección que define el cursor del ratón, o bien cuerpo a cuerpo deslizandonos y dando una estocada de menor rango en esta misma dirección. De tener suficiente tiempo queremos implementar también sistemas de esquiva, bloqueo, ataques cargados, etc.
El resto de los enemigos seguirán la misma mecánica, y habrá que acabar con todos ellos para seguir adelante.
Nuestro personaje contará con una barra de vida. La vida bajará de forma típica en un videojuego, y se podrá rellenar en algunas de la mazmorra. Las habilidades que añadamos se podrán visualizar también, así como el enfriamiento de estas habilidades, que no podremos usar hasta pasado un tiempo dado.

---
## Arte conceptual

![Logo videojuego](/Artwork/personajes-concept-1.PNG)
![Logo videojuego](/Artwork/personajes-concept-2.PNG)
![Logo videojuego](/Artwork/personajes-concept-3.PNG)
