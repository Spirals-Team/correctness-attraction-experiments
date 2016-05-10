import six
import numpy as np
import matplotlib.pyplot as plt
from matplotlib import colors

colors_ = list(six.iteritems(colors.cnames))

blacklist_color = ['#f5fffa','#ffffff','#000000','#008000']

# Add the single letter colors.
for name, rgb in six.iteritems(colors.ColorConverter.colors):
    hex_ = colors.rgb2hex(rgb)
    if str(hex_) not in blacklist_color:
        colors_.append((name, hex_))

padding = 3
colors_ = filter(lambda c: c[1] not in blacklist_color, colors_)

def getColor(indexLoc):
    return colors_[ (indexLoc + padding) % len(colors_)][1]