# clj-hue

A Clojure library for interacting with the Hue API.

## Features

- Discover bridges
- Authenticate with a bridge
- Retreive configuration information
- Control lights

## Todo

- Control groups
- Support for discovering lights

## Usage

```clojure
(:require [clj-hue.bridge :as bridge]
          [clj-hue.light :as light])
;; You should press the pairing button on your Hue Bridge before running this.
(-> (bridge/discover)
    (bridge/register)
    (lights/all)
    (clojure.pprint/pprint))
```

## License

Copyright © 2017 Danielle Tomlinson

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
