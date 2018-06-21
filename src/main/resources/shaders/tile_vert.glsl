#version 140

in vec2 position;
// in vec2 textureCoords;
// in vec3 normal;

// out vec2 pass_TextureCoords;

uniform vec4 viewMatrix;
uniform vec4 transformationMatrix;

void main() {
	gl_Position = vec4(position, 0.0, 1.0);
	// pass_TextureCoords = textureCoords;
}
