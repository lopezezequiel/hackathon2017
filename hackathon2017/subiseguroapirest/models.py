from django.db import models
#from django.contrib.gis.geos import Polygon

# Create your models here.
class BusLine(models.Model):
	name = models.CharField(max_length=30)
	#polygon = models.Polygon

class BusStop(models.Model):
    #point =  models.Point

class BusLineStop(models.Model):
	bus_line = models.ForeignKey('BusLine', on_delete=models.CASCADE)
	bus_stop = models.ForeignKey('BusLine', on_delete=models.CASCADE)

