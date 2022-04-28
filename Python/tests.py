from decimal import Decimal


def test(current):
    """ Test """
    print(isinstance(current, float) or isinstance(current, int))
    if isinstance(current, float):
        current = '{0:.4f}'.format(current)
        print(current)
    else:
        try:
            current = float(current)
            if current < 40 or current > 50:
                raise ValueError
        except ValueError:
            print("Insert a valid number!")


test(45)