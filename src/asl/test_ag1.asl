// Agent test_ag1 in project MiEnv

/* Initial beliefs and rules */

/* Initial goals */

!start.

/* Plans */

+!start : true <- .print("hello world.").

+!random_turn : .random(R) <- 
	turn(R);
	.print("turn");
	.print(R).

-!random_turn : true <- 
	.print("turn failed"); 
	.print(R).

+has_action : true <- !randomTurn.
