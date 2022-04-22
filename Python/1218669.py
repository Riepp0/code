def massimo(a,b):
    if a == b:
        return 0
    elif a > b:
        return 1
    else:
        return -1


def moltiplicatrice(lista_numeri):
    if(len(lista_numeri) == 0):
        return 0
    else:
        totale = 1
        for i in range(len(lista_numeri)):
            totale = totale * lista_numeri[i]
        return totale

def rovescia(stringa):
    if(len(stringa) == 0):
        return "Stringa vuota"
    else:
        s = stringa.lower()
        f = s[0].upper()
        str = f + s[1:]
        stringa = str[::-1]
        return stringa

def frequenza(stringa):
    if(len(stringa) == 0):
        return "Stringa vuota"
    else:
        diz = {}
        for i in range(len(stringa)):
            if(stringa[i] in diz):
                diz[stringa[i]] = diz[stringa[i]] + 1
            else:
                diz[stringa[i]] = 1
        return diz


