{
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
    systems.url = "github:nix-systems/default";
  };
  outputs = { systems, nixpkgs, ... }:
    let
      eachSystem = f:
        nixpkgs.lib.genAttrs (import systems)
        (system: f nixpkgs.legacyPackages.${system});
    in {
      devShells = eachSystem (pkgs: {
        default = pkgs.mkShell {
          buildInputs = with pkgs; [
            gnumake
            gnutar
            zip
            metals
            scalafmt
            scala_3
            scalafmt
            scalafix
            scala-cli
            nixfmt
            statix
            deadnix
            sbt
            texliveMedium
          ];
        };
      });
    };
}
