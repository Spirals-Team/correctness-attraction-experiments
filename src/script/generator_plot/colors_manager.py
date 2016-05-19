import six
import numpy as np
import matplotlib.pyplot as plt
from matplotlib import colors
from matplotlib.colors import hex2color, rgb2hex

colors_ = list(six.iteritems(colors.cnames))

colors_ = filter(lambda c : sum(hex2color(c[1])) < 2.0, colors_)

# Add the single letter colors.
#for name, rgb in six.iteritems(colors.ColorConverter.colors):
#    hex_ = colors.rgb2hex(rgb)
#    colors_.append((name, hex_))

def getColor(indexLoc):
    return colors_[(indexLoc) % len(colors_)][1]