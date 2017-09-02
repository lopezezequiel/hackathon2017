from django.contrib import admin
from .models import BusLine, BusLineStop, BusStop


@admin.register(BusLine)
class BusLineAdmin(admin.ModelAdmin):
    pass


@admin.register(BusLineStop)
class BusLineStopAdmin(admin.ModelAdmin):
    pass


@admin.register(BusStop)
class BusStopAdmin(admin.ModelAdmin):
    pass
