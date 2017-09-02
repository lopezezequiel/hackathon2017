[![Build Status](https://travis-ci.org/lopezezequiel/hackathon2017.svg?branch=master)](https://travis-ci.org/lopezezequiel/hackathon2017)
[![Coverage Status](https://coveralls.io/repos/github/lopezezequiel/hackathon2017/badge.svg?branch=master)](https://coveralls.io/github/lopezezequiel/hackathon2017?branch=master)
[![Code Climate](https://codeclimate.com/github/lopezezequiel/hackathon2017/badges/gpa.svg)](https://codeclimate.com/github/lopezezequiel/hackathon2017)


# subiseguro

## Descripción
Subiseguro consiste en una App mobile mediante la cual podemos conocer cuanto tiempo falta para el arribo de la linea de colectivo que estemos 
esperando.

Lo innovador del proyecto es que funciona de manera colaborativa. Si la app detecta que el usuario esta estacionario en algún sitio, se verifica si el mismo se corresponde a una parada de colectivo. En caso afirmativo se le pregunta que linea esta esperando, y una vez seleccionado, se informara el tiempo de arribo.
Si la app detecta un movimiento del usuario que se corresponde con el patron de movimiento del colectivo comienza a enviar periodicamente las coordenadas a una API REST.
De estos datos (de multiples usuarios) son de los cuales el sistema se alimenta para poder calcular:
 * Tiempo de arribo
 * Velocidad
 * Desvíos
 * Demoras

Si bien es necesario un administrador que cargue los recorridos y paradas, el sistema puede emitir alertas cuando detecta cambios en estos.
Ademas se podría agregar opciones para que los usuarios reporten situaciones (choques, cortes, embotellamientos, etc).

Para fomentar el uso del sistema se ofrecen servicios adicionales como permitirle a los usuarios compartir la ubicación con sus allegados o alertar cuando esta por llegar a su destino (ideal para pasajeros dormilones).

## Herramientas
 * IC (coveralls, codeclimate, travis,
 * Python
 * django, djangorestframework
 * Android
 * PostgreSQL + PostGIS

## Docs
 * [Read the docs](https://readthedocs.org/projects/hackathon2017/)

## App
 * [subiseguro](https://subiseguro.herokuapp.com/)
 
