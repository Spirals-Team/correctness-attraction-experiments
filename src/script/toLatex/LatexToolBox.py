
def barchart(success):
    return '\\textendash' * int((float(success.replace(",", "."))) / 10) + " { } " if float(success.replace(",", ".")) > 10 else ""

def simplePerc(success):
    return str(int(float(success.replace(",", ".")))) + " \\%"