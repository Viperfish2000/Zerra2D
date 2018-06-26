#version 330

in vec2 pass_TextureCoords;

out vec4 out_Color;

uniform sampler2D guiTexture;

void main() {
	out_Color = texture(guiTexture, pass_TextureCoords);
}
