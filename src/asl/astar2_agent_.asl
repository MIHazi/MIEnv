// Agent astar2_agent_ in project MiEnv

/* Initial beliefs and rules */

/* Initial goals */

!start.

/* Plans */

//At start
+!start : start_pos(S) & node([S,SX,SY]) & end_pos(E) & node([E,EX,EY])<-
	+os([[math.sqrt((EX-SX)*(EX-SX)+(EY-SY)*(EY-SY)),0,S]]);		//We add the starting point to the open set (F(N), G(N), N)
	!get_route.												//And start searching for a route

//If the lowest value point is our goal, we have our route
+!get_route : os(O) & .min(O, [_,_,P]) & end_pos(P) <-
	!create_route(P);		//We backtrack the route
	has_route.			//And indicate that we finished

//If the open set is empty, there is no route
+!get_route : os(O) & .length(O,0) <-
	.fail.		//Which means we failed

//When searching for route we get the member of the open set with the minimum F(N)
+!get_route : os(O) & .min(O, [F,G,P]) <-
	+checked([]);				//We create an empty checked list
	!check_neighbor(P);			//Check its neighbors
	-checked(_);				//When it's finished we remove the checked markers
	?os(O2);
	.delete([F,G,P],O2,O3);		//Remove the current best from the open set
	-+os(O3);
	!get_route.					//And start looking for the next point
	
//We only check neighbors we haven't yet checked
//If it isn't in the open set
+!check_neighbor(N) : os(O) & road([_,N,E,_,L]) & checked(C) & not .member(E,C) & not .member([_,_,E],O) &
			node([E,EX,EY]) & end_pos(P) & node([P,PX,PY]) & .member([_,G,N],O) <-
	.concat(O,[[math.sqrt((PX-EX)*(PX-EX)+(PY-EY)*(PY-EY)) + G + L, G + L, E]],O2);	//We add it to the open set
	-+os(O2);
	.concat(C,[E],C2);
	-+checked(C2);															//Mark it as checked
	+route_to(E,N);															//Add it to our route
	!check_neighbor(N).														//And check other neighbors
	
//If it is in the open set
+!check_neighbor(N) : os(O) & road([_,N,E,_,L]) & checked(C) & not .member(E,C) & .member([_,G1,E],O) &
			node([E,EX,EY]) & end_pos(P) & node([P,PX,PY]) & .member([_,G,N],O) <-
	if(G+L < G1){																	//If the new route is better
		.delete([_,_,E],O,O2);														//We delete the old values from the OS
		.concat(O2,[[math.sqrt((PX-EX)*(PX-EX)+(PY-EY)*(PY-EY)) + G + L, G + L, E]],O3);		//Replace them wit new ones
		-+os(O3);
		-+route_to(E,N);															//And replace the route to this point
	}
	.concat(C,[E],C2);
	-+checked(C2);																	//We mark it as checked
	!check_neighbor(N).

+!check_neighbor(_) : true.															//And check other neighbors
	
//While backtracking the route
//If we found the starting point as the next point
+!create_route(P) : route_to(P,N) & start_pos(N) & road([I,N,P,_,_]) <-
	+route(N,I).	//We add it to the route

//If we aren't at the start point
+!create_route(P) : route_to(P,N) & road([I,N,P,_,_]) <-
	+route(N,I);		//We add the next point to the route
	!create_route(N).	//And go to the next point

//We turn on the given road
+!turn_to(I) : true <-
	turn(I).
	
//If we failed, we retry on the next "has_action", or in 50ms
-!turn_to(_) : true <-
	.wait("+has_action", 50).

//If we are on a node, and it is our goal
+has_action : current_pos(P) & end_pos(P) <-
	.print("finished");
	exit_sim.			//We exit the simulation

//If we are at a point which is in the route
+has_action : current_pos(P) & route(P,I) <-
	!turn_to(I).		//We turn to the next road of the route
