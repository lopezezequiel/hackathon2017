# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from rest_framework import serializers

from subiseguroapirest.models import BusLine, BusStop, BusLineStop


class BusLineSerializer(serializers.ModelSerializer):
    """Serializador/Deserialiador de mensajes
    El campo id es de solo lectura
    """

    class Meta:
        model = BusLine
        fields = ('id', 'name')
        extra_kwargs = {
            'id': {u'read_only': True},
            'name': {u'read_only': True}
        }
