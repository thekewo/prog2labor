... // in the beginning: V(G) = 1

// +2 conditions, V(G) = 3:
if ((i > 13) || (i < 15)) {

  System.out.println("Hello, there!");

  // +3 conditions, V(G) = 6:
  while ((i > 0) || ((i > 100) && (i < 999))) {
    ...
  }
}

// +1 condition, V(G) = 7
i = (i == 10) ? 0 : 1;

switch(a) {
  case 1: // +1, V(G) = 8
    break;
  case 2: // +1, V(G) = 9
  case 3: // +1, V(G) = 10
    break;
  default:
    throw new RuntimeException("a = " + a);
}