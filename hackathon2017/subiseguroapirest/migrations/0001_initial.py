# -*- coding: utf-8 -*-
# Generated by Django 1.11.4 on 2017-09-02 19:06
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='BusLine',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=30)),
            ],
        ),
        migrations.CreateModel(
            name='BusLineStop',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('bus_line', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='subiseguroapirest.BusLine')),
            ],
        ),
        migrations.CreateModel(
            name='BusStop',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
            ],
        ),
        migrations.AddField(
            model_name='buslinestop',
            name='bus_stop',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='subiseguroapirest.BusStop'),
        ),
    ]