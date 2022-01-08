import sys
from sympy import diff, Symbol
from sympy.parsing.sympy_parser import parse_expr


def do_something(Equation):
    my_symbols = {'x': Symbol('x', real=True)}
    my_func = parse_expr(Equation, my_symbols)

    print(diff(my_func, my_symbols['x']))


if __name__ == '__main__':
    # Map command line arguments to function arguments.
    do_something(*sys.argv[1:])
