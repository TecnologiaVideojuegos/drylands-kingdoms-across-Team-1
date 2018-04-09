# Documento de diseño de Dryland: Kingdoms Across

## Introducción
Dryland: Kingdoms Across es un proyecto de videojuego de carácter indie, cuyo equipo de desarrollo incluye a Marino Tejedor, Alvaro de las Heras, David Valdevira y Adrian Sánchez.

El videojuego está orientado a ser calificado para la asignatura de Tecnología de Videojuegos en la UAH. A pesar de ello, el propósito como el de todo videojuego es de ser entretenido de diferentes formas.

La mecánica que vamos a detallar después es simple pero se ha demostrado en titulos similares que es divertida, y la historia está pensada para tener un transfondo y hacer pensar al jugador, y no sólo dar contexto a la acción.

La idea principal es simple y concisa, queremos un juego entretenido con una historia simple, pero sólida y con la que sea fácil empatizar con el protagonista. Vamos a huir en este sentido de dobles sentidos, drama o mensajes filosóficos puesto que no vamos a poder darle forma como se merece y nos gustaría.
 
El vector principal con el que transmitiremos todo esto será la narración de la historia entre la batalla, los diálogos, o los pensamientos del personaje, pues en el apartado gráfico o sonoro vamos a usar mayoritariamente recursos gratuitos de Internet. Una vez explicado esto, pasaremos al detalle en cada uno de los apartados.
 
 ---
 ## Historia del videojuego
 
NOTA!!!Falta puntualizar un par de detalles y concretar los nombres.

---
## Look and feel

Dryland: Kingdoms Across está diseñado para ser un videojuego en perspectiva aérea, con camára fija, con nuestro protagonista moviendose por mazmorras en un mundo 2D. La perspectiva que usaremos será ortogonal, aunque nuestro personaje no estará limitado a la cuadricula sino que deambulará en libre movimiento y en libre ángulo, con el control del teclado, sobre el plano 2D que hemos indicado.

Todo el arte incorporado, los entornos, menús, personajes y enemigos son del estilo Pixel Art, en su mayoría a 16bits escalados en una escala x3, con un estilo similar a este:

NOTA, insertar imagen

Buscamos un estilo elegante y sobrio, con detalles minimalistas y sin recargar. Para potenciar el efecto artístico del videojuego, vamos a intentar si está en nuestro plazo de tiempo además añadir efectos, sombras e iluminación híbrida, es decir, en toda la resolución posible, con degradados limpios y suaves que contrasten con el estilo pixel art del resto del juego.

Los tiles usados serán principalmente los mismos, modificados para nuestra ocasión en los detalles que necesitemos, dando diferentes colores dependiendo de si estamos en diferentes mazmorras, etc. Algún escenario será de exterior, pero la idea principal es que sea para contar alguna historia y que estos escenarios no formen parte del gameplay. Como detalle adicional y para dar realismo gráfico, contemplamos la idea de añadir paralelaje a nuestro render con un fondo que se mueva a velocidad diferente de la cámara para aparentar un falso 3D, al estilo Disney en sus películas de animación por capas.

---
## Mazmorras

En esencia nuestro juego es un dungeon crawler. Cada acto consistirá en uno o varios mapas de mazmorras, con diferentes salas unidas por pasillos, con un tamaño y tipo específico de salas dependiendo del acto en el que estemos. Tendremos que recorrer las mazmorras para perseguir un objetivo específico, que puede estar cerca o muy lejos del punto de partida. Para ello, generaremos de forma procedural la mazmorra de acuerdo a unos parámetros cada vez que entremos en un acto nuevo, ya sea por avance en la historia o por perder la partida.

Esto tiene una justificación desde un punto de vista de la historia, como hemos visto las ruinas que nos dejan nuestros antepasados están protegidas por una tecnología desconocida que hace que cada vez que entremos y salgamos las ruinas sean diferentes.

---
## Mecánicas y control

La mecánica de este videojuego no es algo radicalmente novedoso, sino que ya es jugable (o versiones parecidas) en títulos en el mercado actual, algunos con bastante éxito. La idea es movernos en un plano 2D libremente, a través de un mapa tileado, recorriendo una mazmorra para encontrar el objetivo especificado, la mayoría de las veces atravesando salas con bosses, o salas con enemigos menores. El modo de combate que esperamos implementar es, o bien a distancia, disparando en la dirección que define el cursor del ratón, o bien cuerpo a cuerpo deslizandonos y dando una estocada de menor rango en esta misma dirección. De tener suficiente tiempo queremos implementar también sistemas de esquiva, bloqueo, ataques cargados, etc.
El resto de los enemigos seguirán la misma mecánica, y habrá que acabar con todos ellos para seguir adelante.
Nuestro personaje contará con una barra de vida. La vida bajará de forma típica en un videojuego, y se podrá rellenar en algunas de la mazmorra. Las habilidades que añadamos se podrán visualizar también, así como el enfriamiento de estas habilidades, que no podremos usar hasta pasado un tiempo dado.
